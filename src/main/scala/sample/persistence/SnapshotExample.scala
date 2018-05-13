package sample.persistence

import serize._
import akka.actor._
import akka.persistence._

object SnapshotExample extends App {
  import AkkaPersistenceSamplePicklers._

  @serize("Snapshot.Message")
  case class Message(@id(0)evt: String)

  @serize("Snapshot.State")
  final case class ExampleState(@id(0)received: List[String] = Nil) {
    def updated(s: String): ExampleState = copy(s :: received)
    override def toString = received.reverse.toString
  }

  class ExamplePersistentActor extends PersistentActor {
    def persistenceId: String = "sample-id-3"

    var state = ExampleState()

    def receiveCommand: Receive = {
      case "print"                               => println("current state = " + state)
      case "snap"                                => saveSnapshot(state)
      case SaveSnapshotSuccess(metadata)         => // ...
      case SaveSnapshotFailure(metadata, reason) => // ...
      case s: String =>
        persist(Message(s)) { msg => state = state.updated(msg.evt) }
    }

    def receiveRecover: Receive = {
      case SnapshotOffer(_, s: ExampleState) =>
        println("offered state = " + s)
        state = s
      case Message(evt) =>
        state = state.updated(evt)
    }

  }

  val system = ActorSystem("example")
  val persistentActor = system.actorOf(Props(classOf[ExamplePersistentActor]), "persistentActor-3-scala")

  persistentActor ! "a"
  persistentActor ! "b"
  persistentActor ! "snap"
  persistentActor ! "c"
  persistentActor ! "d"
  persistentActor ! "print"

  Thread.sleep(10000)
  system.terminate()
}
