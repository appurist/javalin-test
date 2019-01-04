package com.appurist;

import io.javalin.Javalin;

public class JavalinTest {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.before("/some-path/*", ctx -> {
            // runs before all request to /some-path/*
        });
        app.before(ctx -> {
            // calls before("/*", handler)
        });

        /* Straight from the example page
            at https://javalin.io/documentation#handler-groups
            yet fails to compile, even with Javalin 2.5.0 ...
        app.routes(() -> {
            path("users", () -> {
                get(UserController::getAllUsers);
                post(UserController::createUser);
                path(":id", () -> {
                    get(UserController::getUser);
                    patch(UserController::updateUser);
                    delete(UserController::deleteUser);
                });
            });
        });
        */

        app.get("/hello/:name", ctx -> {
            ctx.result("Hello: " + ctx.pathParam("name"));
        });
        app.get("/hello/*/and/*", ctx -> {
            ctx.result("Hello: " + ctx.splat(0) + " and " + ctx.splat(1));
        });
        app.get("/", ctx -> ctx.result("Hello World"));
    }
}