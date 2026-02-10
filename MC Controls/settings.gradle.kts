rootProject.name = "MC-Controls"

include(
    "banner",
    "bootstrap",
    "domain:validation",
    "frontend",
    "spring",

    // IAM
    "iam",
    "iam:adapter-in-cli",
    "iam:adapter-in-web",
    "iam:adapter-out-persistence",
    "iam:adapter-out-security",
    "iam:application",
    "iam:bootstrap",
    "iam:domain",

)