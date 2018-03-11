package example.endpoints

import javax.ws.rs.Path

import akka.http.scaladsl.server.Directives
import example.DefaultJsonFormats
import example.models._
import io.swagger.annotations._

@Api(
  value = "/pet", //tags in endpoint
  consumes = "application/json, application/xml",
  produces = "application/json, application/xml"
)
@Path("/pet")
trait PetEndPoint extends Directives with DefaultJsonFormats {
  val petRoute = path("pet") {
    addPet ~ updatePet
  }
val a =       new Authorization(value = "petstore_auth", scopes = Array(
  new AuthorizationScope(scope ="write:pets", description = ""),
  new AuthorizationScope(scope ="read:pets",  description = ""),
)
)
  @ApiOperation(
    value = "Add a new pet to the store",
    nickname /*operationId*/= "addPet",
    httpMethod = "POST",
    response = classOf[Pet],
    authorizations = Array(

 a   )
  )
  @ApiImplicitParams(Array(
    new ApiImplicitParam(
      name = "body",
      paramType = "body",
      value = "Pet object that needs to be added to the store",
      required = true,
      dataTypeClass = classOf[Pet]
    )
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 405, message = "Invalid Input")
  ))
  def addPet =
      post {
        complete { "pet" }
      }

  @ApiOperation(
    value = "Update an existing pet",
    nickname /*operationId*/= "updatePet",
    httpMethod = "PUT",
    response = classOf[Pet],
    authorizations = Array(
      new Authorization(
        value = "petstore_auth",
        scopes = Array(
          new AuthorizationScope(scope ="write:pets", description = ""),
          new AuthorizationScope(scope ="read:pets", description = ""),
        )
      )
    )
  )
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", paramType = "body", value = "Pet object that needs to be added to the store", required = true, dataTypeClass = classOf[Pet])
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Invalid Id Supplied"),
    new ApiResponse(code = 404, message = "Pet not found"),
    new ApiResponse(code = 405, message = "Validation Exception")
  ))
  def updatePet =
      post {
        complete { "pet" }
      }

}
