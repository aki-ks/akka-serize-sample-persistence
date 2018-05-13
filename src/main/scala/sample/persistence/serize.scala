package sample.persistence

import serize._

object AkkaPersistenceSamplePicklers extends Default {
  val container = Container
    .withCaseClass[SnapshotExample.ExampleState]
    .withCaseClass[SnapshotExample.Message]

    .withCaseClass[Evt]("Evt")(F(0).data)
    .withCaseClass[ExampleState]("ExampleState")(F(0).events)
}

/** This class is configured (see application.conf) to be used as serializer for all objects of type `serize.SerizeMessage` */
class AkkaPersistenceSampleSerializer extends SerizeRemoteSerializer(AkkaPersistenceSamplePicklers)