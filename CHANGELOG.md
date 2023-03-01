# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.8.1] - March 1st, 2023

Misc updates

## [0.8.0] - October 3rd, 2022

Still trying to fix publishing issue, also dropped dokka, will be replaced with gitbook soon

## [0.7.1] - April 19th, 2022

Just a version bump to try to fix maven central issue

## [0.7.0] - February 12th, 2022
### Changed
- Core module is now multiplatform (Faker implementation on JVM only)
- Generator module is now multiplatform
- Mutator module is now multiplatform
- Dropped Kaml and converted all yaml files to json to enable mpp support

## [0.6.4] - January 11th, 2022
### Changed
- Fixed bug where library version was null
- Fixed bug where doc modules were missing

## [0.6.3] - January 11th, 2022
### Changed
- Documentation Generation Pipeline now requires publishing to succeed
- Releases are no longer published to GitHub

## [0.6.2] - January 11th, 2022
### Changed
- Fixed Doc Generation
- Hopefully Fixed Library Publishing

## [0.6.1] - January 11th, 2022
### Added
- Nexus Plugin to enable publishing to Sonatype

## [0.6.0] - January 10th, 2022
### Added
- Module to contain experimental (read: non-functional) multiplatform port.

## [0.5.4] - January 8th, 2022
### Added
- More documentation

## [0.5.3] - January 8th, 2022
### Changed
- Moved docs to docs folder, so I can doc as per the GitHub Docs

## [0.5.2] - January 8th, 2022
### Changed
- Patch for a bug in the documentation pipeline caused by trying to push to a protected branch

## [0.5.1] - January 8th, 2022
### Added
- Dokka Documentation
### Changed
- Killed the catalog

## [0.5.0] - January 8th, 2022 
### Added
- Correlated Property Generator

### Changed
- Moved to Sourdough gradle plugin
- Generator now takes seed as parameter
- Package name update
- Dependency name update

## [0.4.2] - August 30th, 2021

### Changed

- Cleaned up a minor issue under the hood

## [0.4.1] - May 30th, 2021

### Changed

- Typo

## [0.4.0] - May 30th, 2021

### Added

- Code coverage using jacoco
- Code coverage publishing to CodeCov

## [0.3.0] - May 25th, 2021

### Added

- US Address generators
- English name generators
- YAML parser for easy serialization of large text arrays
- Mutator for modifying existing generators

## [0.2.0] - May 24th, 2021

### Added

- Maven Central integration

### Removed

- Locale as a parameter

## [0.1.1] - May 23rd, 2021

### Added

- Sample generators for Beer and Barcodes

## [0.1.0] - May 21st, 2021

### Added

- Core generation interfaces and classes
- Updated README

### Misc

- Had an absolute banger of a coding sesh 🧀

## [0.0.1] - May 18th, 2021

- Initial PoC with a couple misc endpoints
