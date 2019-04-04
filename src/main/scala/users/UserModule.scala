package users

import com.google.inject.AbstractModule

class UserModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Repository]).to(classOf[UserRepository])
  }
}
