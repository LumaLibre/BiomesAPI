package dev.wyck.test;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that every {@link dev.wyck.factory.WireProvider} template string points at a class that
 * actually exists in the source tree. This is the guard that catches a package move or a refactor
 * or something of that like.
 */
class WireProviderResolutionTest {

    private static final Pattern TEMPLATE = Pattern.compile("(?:WireProvider|Decoder)\\.(?:create|construct)\\(\"([^\"]+)\"\\)");
    private static final Pattern VERSION_DIR = Pattern.compile("^v\\d\\w*$");

    @Test
    void everyWireProviderTemplatePointsAtRealSource() throws IOException {
        Path repoRoot = repoRoot();
        List<Path> sourceRoots = sourceRoots(repoRoot);
        assertFalse(sourceRoots.isEmpty(), "no src/main/java roots found under " + repoRoot);

        Set<String> declaredClasses = declaredClasses(sourceRoots);
        Set<String> versionIds = versionIds(sourceRoots);

        List<String> failures = new ArrayList<>();
        for (Path root : sourceRoots) {
            try (Stream<Path> files = Files.walk(root)) {
                for (Path file : (Iterable<Path>) files.filter(WireProviderResolutionTest::isJavaFile)::iterator) {
                    Matcher matcher = TEMPLATE.matcher(Files.readString(file));
                    while (matcher.find()) {
                        String template = matcher.group(1);
                        if (!resolves(template, declaredClasses, versionIds)) {
                            failures.add(repoRoot.relativize(file) + " template '" + template + "' matches no class in the source tree");
                        }
                    }
                }
            }
        }

        assertTrue(failures.isEmpty(), "WireProvider templates pointing at missing classes:\n  " + String.join("\n  ", failures));
    }

    private static boolean resolves(String template, Set<String> declaredClasses, Set<String> versionIds) {
        Set<String> candidates = new LinkedHashSet<>();
        if (template.contains("*?")) {
            for (String v : versionIds) candidates.add(template.replace("*?", v));
            candidates.add(template.replace("*?.", ""));
        } else if (template.contains("*")) {
            for (String v : versionIds) candidates.add(template.replace("*", v));
        } else {
            candidates.add(template);
        }

        for (String candidate : candidates) {
            int nested = candidate.indexOf('$');
            String topLevel = nested < 0 ? candidate : candidate.substring(0, nested);
            if (declaredClasses.contains(topLevel)) return true;
        }
        return false;
    }

    private static Set<String> declaredClasses(List<Path> sourceRoots) throws IOException {
        Set<String> classes = new HashSet<>();
        for (Path root : sourceRoots) {
            try (Stream<Path> files = Files.walk(root)) {
                files.filter(WireProviderResolutionTest::isJavaFile).forEach(file -> {
                    String rel = root.relativize(file).toString();
                    rel = rel.substring(0, rel.length() - ".java".length());
                    classes.add(rel.replace(File.separatorChar, '.'));
                });
            }
        }
        return classes;
    }

    private static Set<String> versionIds(List<Path> sourceRoots) throws IOException {
        Set<String> ids = new HashSet<>();
        for (Path root : sourceRoots) {
            Path wyck = root.resolve("dev").resolve("wyck");
            if (!Files.isDirectory(wyck)) continue;
            try (Stream<Path> children = Files.list(wyck)) {
                children.filter(Files::isDirectory)
                        .map(p -> p.getFileName().toString())
                        .filter(name -> VERSION_DIR.matcher(name).matches())
                        .forEach(ids::add);
            }
        }
        return ids;
    }

    private static List<Path> sourceRoots(Path repoRoot) throws IOException {
        Path suffix = Path.of("src", "main", "java");
        List<Path> roots = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(repoRoot)) {
            walk.filter(Files::isDirectory).filter(p -> p.endsWith(suffix)).forEach(roots::add);
        }
        return roots;
    }

    private static boolean isJavaFile(Path path) {
        return path.toString().endsWith(".java");
    }

    private static Path repoRoot() {
        String prop = System.getProperty("repo.root");
        if (prop != null) {
            return Path.of(prop);
        }

        Path dir = Path.of("").toAbsolutePath();
        while (dir != null && !Files.exists(dir.resolve("settings.gradle.kts"))) {
            dir = dir.getParent();
        }
        return Objects.requireNonNull(dir, "could not locate repo root (no settings.gradle.kts found?)");
    }
}
