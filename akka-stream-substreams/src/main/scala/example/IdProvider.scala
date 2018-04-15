package example

class IdProvider {
  var currentId = 0

  def getId: Int = this.synchronized {
    currentId = currentId + 1
    currentId
  }
}