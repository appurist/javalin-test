package com.appurist;

import io.javalin.Javalin;
import io.javalin.Context;
import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinTest {
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.requestLogger((ctx, timeMs) -> {
            // prints "GET /hello took 4.5 ms"
            System.out.println(ctx.method() + " "  + ctx.path() + " took " + timeMs + " ms");
        });

        app.before("/some-path/*", ctx -> {
            // runs before all request to /some-path/*
        });
        app.before(ctx -> {
            // calls before("/*", handler)
        });


        // from the example page at https://javalin.io/documentation#handler-groups
        app.routes(() -> {
            path("users", () -> {
                get(JavalinTest::getAllUsers);
                post(JavalinTest::createUser);
                path(":id", () -> {
                    get(JavalinTest::getUser);
                    patch(JavalinTest::updateUser);
                    delete(JavalinTest::deleteUser);
                });
            });
        });
        /* the nested calls above are equivalent to this:
        app.get("/users", JavalinTest::getAllUsers);
        app.post("/users", JavalinTest::createUser);
        app.get("/users/:id", JavalinTest::getUser);
        app.patch("/users/:id", JavalinTest::updateUser);
        app.delete("/users/:id", JavalinTest::deleteUser);
        */

        app.post("/hello/:name", ctx -> {
            ctx.result("Hello: " + ctx.pathParam("name"));
        });
        app.get("/hello/*/and/*", ctx -> {
            ctx.result("Hello: " + ctx.splat(0) + " and " + ctx.splat(1));
        });
        app.get("/hello", ctx -> ctx.result("Hello World"));

        // If enabled, {classpath}/static/index.html will be available via http://{host}:{port}/index.html
        // app.get("/" ...) above would override gets to /
        // app.enableStaticFiles("/static");

        // if enabled, available at /webjars/name/version/file.ext
        // app.enableWebJars();

        app.start(7000);
    }

    private static void getAllUsers(Context ctx)
    {
        ctx.result("returns all users");
    }
    private static void createUser(Context ctx)
    {
        ctx.result("creats a user");
    }
    private static void getUser(Context ctx)
    {
        ctx.result("returns a specific user " + ctx.pathParam("id"));
    }
    private static void updateUser(Context ctx)
    {
        ctx.result("updates a specific user " + ctx.pathParam("id"));
    }
    private static void deleteUser(Context ctx)
    {
        ctx.result("deletes a specific user " + ctx.pathParam("id"));
    }
}