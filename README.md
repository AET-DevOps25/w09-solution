<!--
Copyright © TUM AET 2025 - 2025

Licensed under the MIT License

Authors: Benedikt Hofmann, Patrick Stoeckle, and other contributors

SPDX-FileCopyrightText: 2025 TUM AET

SPDX-License-Identifier: MIT
-->

# w09-solution

## Security of the GitHub Repository

In this section, we will discuss the security measures we have implemented in the GitHub repository to ensure the integrity and security of the codebase.

### Code Owners

To reduce the risk of accidental changes to critical files, we use the [CODEOWNERS](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/about-code-owners) file. The file is located in the `.github` folder, and it defines the code owners for the project. The code owners are responsible for reviewing and approving changes to the codebase.

- If not specified otherwise, the code owner are @pstoeckle and @b3n3d17, i.e., one of them must approve the changes.
- Only @b3n3d17 can approve changes to the `/.github/` folder, i.e., the GitHub workflows, and other GitHub-related files.
- Only @pstoeckle can approve changes to the `/.github/CODEOWNERS` file itself.
- Files in the `/demo/` and `/ui/` folders can be approved by anyone who has write access to the repository.

## Security Tools in the CI/CD

We use reusable workflows for this project. Each workflow starts at a `base` CI file, i.e., [`ci.yml`](./.github/workflows/ci.yml), which references the rest. It is important to note that **both** the parent and the child workflow can define permissions. Using permissions in the child and parent workflow, we want to demonstrate that the child workflow can set less/fewer permissions than the parent, and proceed with them.

Here is an exhaustive list of all workflows you can find in the `.github/workflows` folder.

**Security-related workflows:**

- [`java`](.github/workflows/java.yml): this workflow uses a `gradle` action to check the java code and run tests. The `nebula.lint` plugin is used to lint the `build.gradle` file itself. The `jacoco` plugin generates test reports that are later included in the PR. The `com.github.spotbugs` plugin checks for common programming bugs in the java code.
- [`npm-audit`](.github/workflows/npm-audit.yml): this workflow uses [`npm-audit`](https://docs.npmjs.com/cli/v11/commands/npm-audit) action to check the `package.json` and `package-lock.json` files for known vulnerabilities. It is a good practice to run this action to ensure that your JavaScript/TypeScript/NPM dependencies do not contain known vulnerabilities.
- [`gitleaks`](.github/workflows/gitleaks.yml): this workflow uses [`gitleaks`](https://) to find passwords, tokens, private keys, or other credentials accidentally committed to the repository. It is a good practice to run this action to ensure that your repository does not contain any sensitive information that an attacker could use to compromise your system, especially if the repository is public.
- [`build-container`](.github/workflows/build-container.yml): this workflow demonstrates the flexibility of reusable workflows. It encompasses other actions, and combines them in a bundle. It takes care of everything from building a docker image to analyzing the result. More specifically, this workflow entails:
  - [`hadolint`](.github/workflows/hadolint.yml): this workflow uses the [`hadolint`](https://github.com/hadolint/hadolint) to lint the `Dockerfile`. It is a good practice to lint your `Dockerfile`s as it can help catch common mistakes and improve the overall quality of your images.
  - [`docker`](.github/workflows/docker.yml): A common workflow to build the container image, and push it to the GitHub Container Registry (`ghcr`). We need the container image in the container registry to demonstrate other tools that scan the image itself.
  - [`dockle`](.github/workflows/dockle.yml): this workflow uses the [`dockle`](https://github.com/goodwithtech/dockle) to scan the container image for security vulnerabilities.
  - [`trivy`](.github/workflows/trivy.yml): this workflow uses the [`trivy`](https://trivy.dev/latest/) to generate a Software Bill of Materials (SBOM), and find vulnerabilities in the container image based on the SBOM. Furthermore, the SBOM is then signed via [`cosign`](https://github.com/sigstore/cosign) with a key generated via `GITHUB_TOKEN="<your-token>" cosign generate-key-pair github://USERNAME/REPO`.
- [`tflint`](.github/workflows/tflint.yml): this workflow uses [`tflint`](https://github.com/terraform-linters/tflint) to lint the Terraform code.
- [`kics`](.github/workflows/kics.yml): this workflow uses [`kics`](https://kics.io/index.html#) to scan the codebase for security vulnerabilities.
- [`semgrep`](.github/workflows/semgrep.yml): [`semgrep`](https://github.com/semgrep/semgrep) is a static-analysis tool for various languages. Creating an account with the service opens the door to ✨ _AI-enhanced_ ✨ feedback, but the free OSS version can catch some early errors as well.
- [`renovatebot`](https://github.com/renovatebot): GitHub `dependabot` is not the only tool out there to update third-party dependencies. [`renovate`](https://docs.renovatebot.com/) can be added as a separate action, or as a GitHub Application, but you need to create a free account. Since we added it as GitHub Application, there is **NO** renovate workflow file.

**Other workflows:**

- [`spelling`](.github/workflows/spelling.yml): No project looks professional without proper spelling! Here, we use the [`cspell`](https://cspell.org/) and [`typos`](https://github.com/crate-ci/typos) to check the spelling of the codebase. `cspell` checks the documentation, i.e., the `*.md` files, and `typos` the code files, e.g., `*.java` or `*.ts`, and configuration files like `*.yml`.
- [`hawkeye`](.github/workflows/hawkeye.yml): this workflow uses [`hawkeye`](https://github.com/korandoru/hawkeye/) to check if all code files have a correct copyright header; you can also use `hawkeye` to generate the copyright headers.

## Security of the CI/CD

In this section, we will discuss security aspects of the CI/CD pipeline itself.

### Origin of the GitHub Actions

We can categorize the GitHub Actions used in this project into four groups:

**Official GitHub Actions:**

- [`actions/checkout`](https://github.com/actions/checkout)
- [`actions/setup-node`](https://github.com/actions/setup-node)
- [`actions/upload-artifact`](https://github.com/actions/upload-artifact)
- [`github/codeql-action/upload-sarif`](https://github.com/github/codeql-action/upload-sarif)

**GitHub Actions from Well-Known Organizations:**

- [`aquasecurity/trivy-action`](https://github.com/aquasecurity/trivy-action)
- [`checkmarx/kics-github-action`](https://github.com/checkmarx/kics-github-action)
- [`docker/build-push-action`](https://github.com/docker/build-push-action)
- [`docker/login-action`](https://github.com/docker/login-action)
- [`docker/setup-buildx-action`](https://github.com/docker/setup-buildx-action)
- [`docker/setup-qemu-action`](https://github.com/docker/setup-qemu-action)
- [`gitleaks/gitleaks-action`](https://github.com/gitleaks/gitleaks-action)
- [`goodwithtech/dockle-action`](https://github.com/goodwithtech/dockle-action)
- [`hadolint/hadolint-action`](https://github.com/hadolint/hadolint-action)
- [`sigstore/cosign-installer`](https://github.com/sigstore/cosign-installer)
- [`terraform-linters/setup-tflint`](https://github.com/terraform-linters/setup-tflint)

**Smaller Organizations:**

- [`crate-ci/typos`](https://github.com/crate-ci/typos)
- [`streetsidesoftware/cspell-action`](https://github.com/streetsidesoftware/cspell-action)

**Single Contributors or few Contributors:**

- [`burrunan/gradle-cache-action`](https://github.com/burrunan/gradle-cache-action)
- [`korandoru/hawkeye`](https://github.com/korandoru/hawkeye)
- [`madrapps/jacoco-report`](https://github.com/madrapps/jacoco-report)

To state this clearly: We do **NOT** say that the last group is not trustworthy.

### Versions of the GitHub Actions

We use commit SHAs to pin the versions of the GitHub Actions used in this project. This is a good practice to ensure that the CI/CD pipeline is not affected by changes in the GitHub Actions. Furthermore, we use `renovatebot` to update the SHAs of the GitHub Actions automatically, but in a controlled manner.

## Contact

- [b3n3d17](https://github.com/b3n3d17)
- [pstoeckle](https://github.com/pstoeckle)

## Acknowledgements

We would like to thank our working students for their contributions to this repository.
