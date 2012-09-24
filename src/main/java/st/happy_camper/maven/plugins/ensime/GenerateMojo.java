/*
 * Copyright 2012 Happy-Camper Street.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package st.happy_camper.maven.plugins.ensime;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * Generates ENSIME configuration files.
 * 
 * @author ueshin
 */
@Mojo(name = "generate", requiresProject = true, requiresDependencyResolution = ResolutionScope.TEST, aggregator = true)
public class GenerateMojo extends AbstractMojo {

    public static final String DOT_ENSIME = ".ensime";

    /**
     * The project whose project files to create.
     */
    @Component
    protected MavenProject project;

    /**
     * The formatter preferences
     */
    @Parameter(property = "ensime.formatter.preferences", defaultValue = "${basedir}/src/ensime/formatter.properties")
    protected File formatterPreferences;

    /**
     * Skip the operation when true.
     */
    @Parameter(property = "ensime.skip", defaultValue = "false")
    protected boolean skip;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(skip) {
            return;
        }
        ConfigGenerator generator = new ConfigGenerator(project, formatterPreferences);
        generator.generate(new File(project.getBasedir(), DOT_ENSIME));
    }
}
