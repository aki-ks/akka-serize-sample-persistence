organization := "com.typesafe.akka.samples"
name := "akka-sample-persistence-scala"

scalaVersion := "2.12.4"
libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-persistence" % "2.5.12",
  "org.iq80.leveldb"            % "leveldb"          % "0.7",
  "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8"
)

resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "com.github.aki-ks" % "Serize-akka" % "0.1.0"
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))
