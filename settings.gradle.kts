rootProject.name = "todo-platform"

include(
    ":common:domain",
    ":common:service",

    ":apps:authentication:domain",
    ":apps:authentication:service",

    ":apps:item:domain",
    ":apps:item:service",

    ":apps:gateway"
)