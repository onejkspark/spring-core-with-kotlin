tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

//dependencies {
//    // webflux
//    implementation("org.springframework.boot:spring-boot-starter-webflux")
//    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
//
//    //
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
//    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
//
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
//    testImplementation("io.projectreactor:reactor-test")
//}
