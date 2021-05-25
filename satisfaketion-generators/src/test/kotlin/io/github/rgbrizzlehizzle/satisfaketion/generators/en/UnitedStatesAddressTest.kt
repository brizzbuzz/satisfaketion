package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class UnitedStatesAddressTest : DescribeSpec({
  it("Can generate a unique building code") {
    // arrange
    val random = Random(42)
    val address = UnitedStatesAddress(random)

    // act
    val bn = address.buildingNumber.generate()

    // assert
    bn shouldBeExactly 12
  }
  it("Can generate a community with prefix and suffix") {
    // arrange
    val random = Random(9001)
    val address = UnitedStatesAddress(random)

    // act
    val community = address.community.generate()

    // assert
    community shouldBe "Autumn Heights"
  }
  it("Can generate a secondary address") {
    // arrange
    val random = Random(1337)
    val address = UnitedStatesAddress(random)

    // act
    val secondary = address.secondaryAddress.generate()
    val anotherSecondary = address.secondaryAddress.generate()

    // assert
    secondary shouldBe "Suite 059"
    anotherSecondary shouldBe "Suite 425"
  }
  it("Can generate a post code") {
    // arrange
    val random = Random(1337)
    val address = UnitedStatesAddress(random)

    // act
    val postcode = address.postcode.generate()

    // assert
    postcode shouldBe "50591"
  }
  it("Can generate a local post code") {
    // arrange
    val random = Random(420)
    val address = UnitedStatesAddress(random)

    // act
    val postcode = address.postCodeWithLocal.generate()

    // assert
    postcode shouldBe "31823-9165"
  }
  it("Can retrieve post code by state") {
    // arrange
    val random = Random(42)
    val address = UnitedStatesAddress(random)

    // act
    val postcode = address.postcodeByState("MD").generate()
    val localPostcode = address.postcodeByState("MD", true).generate()

    // assert
    postcode shouldBe "21030"
    localPostcode shouldBe "21012-1210"
  }
  it("Can generate a state") {
    // arrange
    val random = Random(42)
    val address = UnitedStatesAddress(random)

    // act
    val state = address.state.generate()

    // assert
    state shouldBe "North Dakota"
  }
  it("Can generate a state code") {
    // arrange
    val random = Random(123)
    val address = UnitedStatesAddress(random)

    // act
    val stateCode = address.stateCode.generate()

    // assert
    stateCode shouldBe "CT"
  }
  it("Can generate an appropriate post code from a generated state code") {
    // arrange
    val random = Random(452)
    val address = UnitedStatesAddress(random)

    // act
    val stateCode = address.stateCode.generate()
    val postalCode = address.postcodeByState(stateCode, true).generate()

    // assert
    stateCode shouldBe "AL"
    postalCode shouldBe "35030-3603"
  }
  it("Can generate a city name") {
    // arrange
    val random = Random(42)
    val address = UnitedStatesAddress(random)

    // act
    val city = address.city.generate()
    val otherCity = address.city.generate()

    // assert
    city shouldBe "Quinnshire"
    otherCity shouldBe "North Lubowitzport"
  }
  it("Can generate a street name") {
    // arrange
    val random = Random(1337)
    val address = UnitedStatesAddress(random)

    // act
    val street = address.streetName.generate()

    // assert
    street shouldBe "New Hickle Track"
  }
  it("Can generate a street address") {
    // arrange
    val random = Random(42)
    val address = UnitedStatesAddress(random)

    // act
    val street = address.streetAddress.generate()

    // assert
    street shouldBe "12 Gray Knolls"
  }
  it("Can generate a full address") {
    // arrange
    val random = Random(1337)
    val address = UnitedStatesAddress(random)

    // act
    val fullAddress = address.fullAddress.generate()

    // assert
    fullAddress shouldBe "5914 East Hanh Street, Suite 004, New Wintheiserberg, VT 05029"
  }
})
