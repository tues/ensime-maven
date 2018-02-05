package org.ensime.maven.plugins.ensime;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.model.Dependency;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.apache.commons.lang3.tuple.Pair;


public class EnsimeConfigGeneratorTest {

    @Test
    public void testGetScalaVersionDefault() {
        List<Dependency> directDependencies = new ArrayList<>();
        List<Dependency> depMgmtDependencies = new ArrayList<>();
        Set<Artifact> allDependencies = new HashSet<>();
        String ensimeScalaVersion = null;
        String defaultScalaVersion = "2.9.6";

        Pair<String, Optional<String>> result = EnsimeConfigGenerator.getScalaVersion(
            directDependencies, depMgmtDependencies, allDependencies, ensimeScalaVersion,
            defaultScalaVersion);

        assertEquals(result.getLeft(), defaultScalaVersion);
        assertEquals(result.getRight(), Optional.empty());
    }

    @Test
    public void testGetScalaVersionDirect() throws Exception {
        Dependency dep = new Dependency();
        dep.setGroupId("org.scala-lang");
        dep.setArtifactId("scala-library");
        dep.setVersion("2.14.3");

        Dependency dep2 = new Dependency();
        dep2.setGroupId("org.scala-lang");
        dep2.setArtifactId("scala-library");
        dep2.setVersion("2.15.4");

        ArtifactHandler ah = new DefaultArtifactHandler();
        VersionRange vr3 = VersionRange.createFromVersionSpec("2.16.5");
        Artifact dep3 = new DefaultArtifact("org.scala-lang", "scala-library", vr3, "compile", "jar", null, ah);
        VersionRange vr4 = VersionRange.createFromVersionSpec("2.16.5");
        Artifact dep4 = new DefaultArtifact("org.scala-lang", "scala-library", vr4, "compile", "jar", null, ah);
        VersionRange vr5 = VersionRange.createFromVersionSpec("2.17.5");
        Artifact dep5 = new DefaultArtifact("org.scala-lang", "scala-library", vr5, "compile", "jar", null, ah);
        VersionRange vr6 = VersionRange.createFromVersionSpec("2.11.8");
        Artifact dep6 = new DefaultArtifact("org.scala-lang", "scala-library", vr6, "compile", "jar", null, ah);

        List<Dependency> directDependencies = new ArrayList<>();
        directDependencies.add(dep);

        List<Dependency> depMgmtDependencies = new ArrayList<>();
        depMgmtDependencies.add(dep2);

        Set<Artifact> allDependencies = new HashSet<>();
        allDependencies.add(dep3);
        allDependencies.add(dep4);
        allDependencies.add(dep5);
        allDependencies.add(dep6);

        String ensimeScalaVersion = null;
        String defaultScalaVersion = "2.10.6";

        Pair<String, Optional<String>> result = EnsimeConfigGenerator.getScalaVersion(
            directDependencies, depMgmtDependencies, allDependencies, ensimeScalaVersion,
            defaultScalaVersion);

        assertEquals(result.getLeft(), "2.14.3");
        assertEquals(result.getRight(), Optional.empty());
    }

    @Test
    public void testGetScalaVersionDepMgmt() throws Exception {
        Dependency dep2 = new Dependency();
        dep2.setGroupId("org.scala-lang");
        dep2.setArtifactId("scala-library");
        dep2.setVersion("2.15.4");

        ArtifactHandler ah = new DefaultArtifactHandler();
        VersionRange vr3 = VersionRange.createFromVersionSpec("2.16.5");
        Artifact dep3 = new DefaultArtifact("org.scala-lang", "scala-library", vr3, "compile", "jar", null, ah);
        VersionRange vr4 = VersionRange.createFromVersionSpec("2.16.5");
        Artifact dep4 = new DefaultArtifact("org.scala-lang", "scala-library", vr4, "compile", "jar", null, ah);
        VersionRange vr5 = VersionRange.createFromVersionSpec("2.17.5");
        Artifact dep5 = new DefaultArtifact("org.scala-lang", "scala-library", vr5, "compile", "jar", null, ah);
        VersionRange vr6 = VersionRange.createFromVersionSpec("2.11.8");
        Artifact dep6 = new DefaultArtifact("org.scala-lang", "scala-library", vr6, "compile", "jar", null, ah);

        List<Dependency> directDependencies = new ArrayList<>();

        List<Dependency> depMgmtDependencies = new ArrayList<>();
        depMgmtDependencies.add(dep2);

        Set<Artifact> allDependencies = new HashSet<>();
        allDependencies.add(dep3);
        allDependencies.add(dep4);
        allDependencies.add(dep5);
        allDependencies.add(dep6);

        String ensimeScalaVersion = null;
        String defaultScalaVersion = "2.10.6";

        Pair<String, Optional<String>> result = EnsimeConfigGenerator.getScalaVersion(
            directDependencies, depMgmtDependencies, allDependencies, ensimeScalaVersion,
            defaultScalaVersion);

        assertEquals(result.getLeft(), "2.15.4");
        assertEquals(result.getRight(), Optional.empty());
    }

    @Test
    public void testGetScalaVersionAllDepSame() throws Exception {
        ArtifactHandler ah = new DefaultArtifactHandler();
        VersionRange vr3 = VersionRange.createFromVersionSpec("2.16.5");
        Artifact dep3 = new DefaultArtifact("org.scala-lang", "scala-library", vr3, "compile", "jar", null, ah);
        VersionRange vr4 = VersionRange.createFromVersionSpec("2.16.5");
        Artifact dep4 = new DefaultArtifact("org.scala-lang", "scala-library", vr4, "compile", "jar", null, ah);

        List<Dependency> directDependencies = new ArrayList<>();

        List<Dependency> depMgmtDependencies = new ArrayList<>();

        Set<Artifact> allDependencies = new HashSet<>();
        allDependencies.add(dep3);
        allDependencies.add(dep4);

        String ensimeScalaVersion = null;
        String defaultScalaVersion = "2.10.6";

        Pair<String, Optional<String>> result = EnsimeConfigGenerator.getScalaVersion(
            directDependencies, depMgmtDependencies, allDependencies, ensimeScalaVersion,
            defaultScalaVersion);

        assertEquals(result.getLeft(), "2.16.5");
        assertEquals(result.getRight(), Optional.empty());
    }

    @Test
    public void testGetScalaVersionAllMajorSame() throws Exception {
        ArtifactHandler ah = new DefaultArtifactHandler();
        VersionRange vr3 = VersionRange.createFromVersionSpec("2.16.5");
        Artifact dep3 = new DefaultArtifact("org.scala-lang", "scala-library", vr3, "compile", "jar", null, ah);
        VersionRange vr4 = VersionRange.createFromVersionSpec("2.16.4");
        Artifact dep4 = new DefaultArtifact("org.scala-lang", "scala-library", vr4, "compile", "jar", null, ah);

        List<Dependency> directDependencies = new ArrayList<>();

        List<Dependency> depMgmtDependencies = new ArrayList<>();

        Set<Artifact> allDependencies = new HashSet<>();
        allDependencies.add(dep3);
        allDependencies.add(dep4);

        String ensimeScalaVersion = null;
        String defaultScalaVersion = "2.10.6";

        Pair<String, Optional<String>> result = EnsimeConfigGenerator.getScalaVersion(
            directDependencies, depMgmtDependencies, allDependencies, ensimeScalaVersion,
            defaultScalaVersion);

        assertEquals(result.getLeft(), "2.16.5");
        String logMessage = "Multiple scala versions detected, using 2.16.5.  " +
            "Use -Densime.scala.version to override.";
        assertEquals(result.getRight(), Optional.of(logMessage));
    }

    @Test
    public void testGetScalaVersionAllMajorDifferent() throws Exception {
        ArtifactHandler ah = new DefaultArtifactHandler();
        VersionRange vr3 = VersionRange.createFromVersionSpec("2.17.8");
        Artifact dep3 = new DefaultArtifact("org.scala-lang", "scala-library", vr3, "compile", "jar", null, ah);
        VersionRange vr4 = VersionRange.createFromVersionSpec("2.16.9");
        Artifact dep4 = new DefaultArtifact("org.scala-lang", "scala-library", vr4, "compile", "jar", null, ah);
        VersionRange vr5 = VersionRange.createFromVersionSpec("2.17.5");
        Artifact dep5 = new DefaultArtifact("org.scala-lang", "scala-library", vr5, "compile", "jar", null, ah);
        VersionRange vr6 = VersionRange.createFromVersionSpec("2.16.5");
        Artifact dep6 = new DefaultArtifact("org.scala-lang", "scala-library", vr6, "compile", "jar", null, ah);

        List<Dependency> directDependencies = new ArrayList<>();

        List<Dependency> depMgmtDependencies = new ArrayList<>();

        Set<Artifact> allDependencies = new HashSet<>();
        allDependencies.add(dep3);
        allDependencies.add(dep4);
        allDependencies.add(dep5);
        allDependencies.add(dep6);

        String ensimeScalaVersion = null;
        String defaultScalaVersion = "2.10.6";

        Pair<String, Optional<String>> result = EnsimeConfigGenerator.getScalaVersion(
            directDependencies, depMgmtDependencies, allDependencies, ensimeScalaVersion,
            defaultScalaVersion);

        String logMessage = "Multiple scala versions detected, using 2.16.9.  " +
            "Use -Densime.scala.version to override.";
        assertEquals(result.getLeft(), "2.16.9");
        assertEquals(result.getRight(), Optional.of(logMessage));
    }

    
}
