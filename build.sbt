name := "shoppingApp"
version := "0.1"

libraryDependencies ++= {
  val akkaVersion = "2.5.12"
  val akkaHttp = "10.1.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core"  % akkaHttp,
    "com.typesafe.akka" %% "akka-http"       % akkaHttp,
    "com.typesafe.play" %% "play-ws-standalone-json"       % "1.1.8",
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "de.heikoseeberger" %% "akka-http-play-json"   % "1.17.0",
    "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.19",
    "com.typesafe.akka" %% "akka-http-testkit"    % "10.1.8"   % "test",
    "org.scalatest"     %% "scalatest"       % "3.0.5"       % "test",
    "org.mongodb.scala" %% "mongo-scala-driver" % "2.3.0",
    "org.mongodb.scala" %% "mongo-scala-bson" % "2.6.0",
    "org.mockito"       %% "mockito-scala" % "1.1.1",
    "com.google.inject" % "guice"       % "4.2.1",
    "ch.qos.logback"    %  "logback-classic" % "1.2.3",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.21" % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % "10.1.8" % Test
  )
}