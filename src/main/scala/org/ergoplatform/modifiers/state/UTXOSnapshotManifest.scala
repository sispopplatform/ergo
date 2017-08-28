package org.ergoplatform.modifiers.state

import io.circe.Json
import org.ergoplatform.modifiers.ErgoPersistentModifier
import org.ergoplatform.modifiers.history.Header
import org.ergoplatform.settings.Algos
import scorex.core.NodeViewModifier.{ModifierId, ModifierTypeId}
import scorex.core.serialization.Serializer

import scala.util.Try

case class UTXOSnapshotManifest(chunkRootHashes: Seq[Array[Byte]], blockId: ModifierId) extends ErgoPersistentModifier {
  override val modifierTypeId: ModifierTypeId = UTXOSnapshotManifest.ModifierTypeId

  override lazy val id: ModifierId = Algos.hash(scorex.core.utils.concatBytes(chunkRootHashes :+ blockId))

  override type M = UTXOSnapshotManifest

  override lazy val serializer: Serializer[UTXOSnapshotManifest] = UTXOSnapshotManifestSerializer

  override lazy val json: Json = ???

  lazy val rootHash: Array[Byte] = Algos.merkleTreeRoot(chunkRootHashes)
}

object UTXOSnapshotManifest {
  val ModifierTypeId: Byte = 106: Byte

  def validate(manifest: UTXOSnapshotManifest, header: Header): Try[Unit] = Try {
    require(manifest.blockId sameElements header.id)
    require(manifest.rootHash sameElements header.stateRoot)
    ???
  }
}

object UTXOSnapshotManifestSerializer extends Serializer[UTXOSnapshotManifest] {
  override def toBytes(obj: UTXOSnapshotManifest): Array[ModifierTypeId] = ???

  override def parseBytes(bytes: Array[ModifierTypeId]): Try[UTXOSnapshotManifest] = ???
}

