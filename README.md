
# 🧪 Nextus-core

> **Reusable, modular and extensible Java + Playwright automation framework**  
> Built for UI + API testing, CI/CD pipelines, and professional test architecture.

## 🚀 Overview

**nextus-core** is a core test automation library built using **Java + Playwright**, designed for:

- UI test automation (web)
- API testing using Playwright's native HTTP client
- Modular configuration via `OWNER`
- Automatic test data generation via `PODAM` & `JavaFaker`
- Extensible with custom assertions, reporting, CI/CD support

This module can be published to Nexus and consumed as a dependency in any test project.

## 🧠 Architecture Summary

```text
com.nextus.framework
├── config/         → Environment & config management (OWNER)
├── annotations/    → Test-level classification (@WebTest, @ApiTest, etc.)
├── base/           → Abstract base classes (BaseTest, BasePage)
├── core/           → Driver management & page factory (Singleton/Factory)
├── factory/        → Config factories (Strategy-compatible)
├── strategy/       → Local/Remote driver or env strategies
├── utils/          → Helpers like WaitUtils, LogUtils, RandomUtils
├── assertions/     → Fluent assertion DSL (NextusAssert)
├── manager/        → Screenshot, Video, Report managers
├── data/
│   ├── model/      → POJOs for test data
│   └── generator/  → Data generators (PODAM, JavaFaker)
└── api/
    ├── context/    → APIRequestContext manager (Playwright)
    ├── base/       → BaseApiTest
    └── client/     → Endpoint-specific API clients
```

## 🧰 Tech Stack

| Layer         | Tool / Lib                      |
|---------------|----------------------------------|
| Language      | Java 21                         |
| Automation    | [Playwright for Java](https://playwright.dev/java/) |
| Test Runner   | JUnit 5                         |
| Config        | [Owner](https://github.com/lviggiano/owner) |
| Data Gen      | [PODAM](https://github.com/mtedone/podam), [JavaFaker](https://github.com/DiUS/java-faker) |
| Assertions    | Fluent custom DSL               |
| Reporting     | Allure / ReportPortal ready     |
| CI/CD         | Jenkins (Declarative Pipeline)  |

## 📦 Features

✅ Modular, reusable structure  
✅ Playwright Web & API test support  
✅ Custom test annotations  
✅ Screenshot & video recording  
✅ Allure-ready report hooks  
✅ Nexus-publishable core library  
✅ Ready for mobile extension (Appium)  
✅ Thread-safe driver + config management  
✅ Configuration per environment (qa, dev, staging...)  

## 🧪 Sample Usage

```java
@WebTest
public class LoginTest extends BaseTest {
    
    @Test
    void user_can_login_successfully() {
        page.navigate(ConfigFactory.getConfig().baseUrl());
        // ... page actions
        NextusAssert.assertThat(page).element(".dashboard").isVisible();
    }
}
```

## 🧬 Configuration

You can create environment-based config files inside:

```text
src/test/resources/config/
├── default.properties
├── qa.properties
└── staging.properties
```

And pass the environment at runtime:
```
 mvn clean test -Denv=qa
```

## 📡 CI/CD Support

This framework is Jenkins-compatible, with:
- Declarative Jenkinsfile
- Allure plugin support
- Nexus deploy stage
- Screenshot & video artifact storage

## 📁 Docs

- `architecture.drawio` → Editable architecture diagram
- `architecture.png` → Visual reference

## 🧱 Future Expansion

- ✅ nextus-core (this module)
- 🔜 nextus-ui → Project-specific page classes
- 🔜 nextus-api → Specialized API wrappers
- 🔜 nextus-mobile → Appium support for mobile automation

## 🤝 Contribution & License

Licensed under MIT. Contributions and PRs are welcome.
