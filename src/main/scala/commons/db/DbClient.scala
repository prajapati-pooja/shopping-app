package commons.db

import javax.inject.Singleton
import org.mongodb.scala.{Document, MongoClient, MongoDatabase}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

@Singleton
class DbClient {
  private val dbConnection = "mongodb://localhost:27017"
  private val dbName = "test"
  private val userCollectionName = "Users"

  val client: MongoClient = MongoClient(dbConnection)
  val db: MongoDatabase = client.getDatabase(dbName)

  def getUserCollection: Future[Seq[JsValue]] = {
    db.getCollection(userCollectionName)
      .find()
      .map((doc: Document) => { Json.parse(doc.toJson()) })
      .toFuture()
  }
}
