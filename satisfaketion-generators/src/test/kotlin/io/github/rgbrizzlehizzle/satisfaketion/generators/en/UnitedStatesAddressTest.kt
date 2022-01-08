package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class UnitedStatesAddressTest : DescribeSpec({
  it("Can generate a unique building code") {
    // arrange
    val random = Random(42)

    // act
    val bn = UnitedStatesAddress.buildingNumber.generate(random)

    // assert
    bn shouldBeExactly 12
  }
  it("Can generate a community with prefix and suffix") {
    // arrange
    val random = Random(9001)

    // act
    val community = UnitedStatesAddress.community.generate(random)

    // assert
    community shouldBe "Autumn Heights"
  }
  it("Can generate a secondary address") {
    // arrange
    val random = Random(1337)

    // act
    val secondary = UnitedStatesAddress.secondaryAddress.generate(random)
    val anotherSecondary = UnitedStatesAddress.secondaryAddress.generate(random)

    // assert
    secondary shouldBe "Suite 059"
    anotherSecondary shouldBe "Suite 425"
  }
  it("Can generate a post code") {
    // arrange
    val random = Random(1337)

    // act
    val postcode = UnitedStatesAddress.postcode.generate(random)

    // assert
    postcode shouldBe "50591"
  }
  it("Can generate a local post code") {
    // arrange
    val random = Random(420)

    // act
    val postcode = UnitedStatesAddress.postCodeWithLocal.generate(random)

    // assert
    postcode shouldBe "31823-9165"
  }
  it("Can retrieve post code by state") {
    // arrange
    val random = Random(42)

    // act
    val postcode = UnitedStatesAddress.postcodeByState("MD").generate(random)
    val localPostcode = UnitedStatesAddress.postcodeByState("MD", true).generate(random)

    // assert
    postcode shouldBe "21030"
    localPostcode shouldBe "21012-1210"
  }
  it("Can generate a state") {
    // arrange
    val random = Random(42)

    // act
    val state = UnitedStatesAddress.state.generate(random)

    // assert
    state shouldBe "North Dakota"
  }
  it("Can generate a state code") {
    // arrange
    val random = Random(123)

    // act
    val stateCode = UnitedStatesAddress.stateCode.generate(random)

    // assert
    stateCode shouldBe "CT"
  }
  it("Can generate an appropriate post code from a generated state code") {
    // arrange
    val random = Random(452)

    // act
    val stateCode = UnitedStatesAddress.stateCode.generate(random)
    val postalCode = UnitedStatesAddress.postcodeByState(stateCode, true).generate(random)

    // assert
    stateCode shouldBe "AL"
    postalCode shouldBe "35030-3603"
  }
  it("Can generate a city name") {
    // arrange
    val random = Random(42)

    // act
    val city = UnitedStatesAddress.city.generate(random)
    val otherCity = UnitedStatesAddress.city.generate(random)

    // assert
    city shouldBe "Quinnshire"
    otherCity shouldBe "North Lubowitzport"
  }
  it("Can generate a street name") {
    // arrange
    val random = Random(1337)

    // act
    val street = UnitedStatesAddress.streetName.generate(random)

    // assert
    street shouldBe "New Hickle Track"
  }
  it("Can generate a street address") {
    // arrange
    val random = Random(42)

    // act
    val street = UnitedStatesAddress.streetAddress.generate(random)

    // assert
    street shouldBe "12 Gray Knolls"
  }
  it("Can generate a full address") {
    // arrange
    val random = Random(1337)

    // act
    val fullAddress = UnitedStatesAddress.fullAddress.generate(random)

    // assert
    fullAddress shouldBe "5914 East Hanh Street, Suite 004, New Wintheiserberg, VT 05029"
  }
})
