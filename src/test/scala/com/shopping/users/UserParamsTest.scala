package com.shopping.users

import com.shopping.BaseTest

class UserParamsTest extends BaseTest {

  test("should create userParams with valid parameters") {
    val params = UserParams.create(Some(23), Some("age   "), Some("DESC   "))
    params shouldEqual UserParams(Some(23), Some("AGE"), Some("DESC"))
  }

  test("should create userParams with none value of sortBy") {
    val params = UserParams.create(Some(23), Some("   "), Some("DESC   "))
    params shouldEqual UserParams(Some(23), None, Some("DESC"))
  }

  test("should create userParams with none value of sortOrder") {
    val params = UserParams.create(Some(23), Some("age   "), Some("   "))
    params shouldEqual UserParams(Some(23), Some("AGE"), None)
  }
}
