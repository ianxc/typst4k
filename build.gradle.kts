plugins {
    `project-report`
}

tasks.named<HtmlDependencyReportTask>("htmlDependencyReport") {
    projects = project.allprojects
}
