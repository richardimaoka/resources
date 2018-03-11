package example

import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model._
import example.endpoints.PetEndPoint
import io.swagger.models.ExternalDocs
import io.swagger.models.auth.{ApiKeyAuthDefinition, In, OAuth2Definition}

object SwaggerDocService extends SwaggerHttpService {
  override val apiClasses = Set(classOf[PetEndPoint])
  override val host = "localhost:9939"
  override val basePath = "v2"
  override val info = Info(
    version = "1.0.0",
    description =
      """This is a sample server Petstore server.  You can find out more about
        |Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).
        |For this sample, you can use the api key `special-key` to test the authorization filters."
      """.stripMargin,
    title = "Swagger Petstore",
    termsOfService = "http://swagger.io/terms/",
    contact = Some(Contact("","","apiteam@swagger.io")),
    license = Some(License("Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0.html"))
  )
  override val externalDocs = Some(new ExternalDocs("Core Docs", "http://acme.com/docs"))

  private val oAuth2Definition = (new OAuth2Definition())
    .scope("write:pets", "modify pets in your account")
    .scope("read:pets", "read your pets")
  // In Java below two fields are set using `implicit` method on OAuth2Definition,
  // but `implicit` is a keyword in Scala... so it is not recognized as a method name,
  // hence using these setters
  oAuth2Definition.setAuthorizationUrl("http://petstore.swagger.io/oauth/dialog")
  oAuth2Definition.setFlow("implicit")

  override val securitySchemeDefinitions = Map(
    "petsotre_auth" -> oAuth2Definition,
    "api_key" -> new ApiKeyAuthDefinition("api_key", In.HEADER)
  )
  override val unwantedDefinitions = Seq("Function1", "Function1RequestContextFutureRouteResult")
}