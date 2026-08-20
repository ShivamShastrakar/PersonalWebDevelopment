--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_CLIENT_ID', 'fd5c0da780f49ada99ca635f77d95c2021f6b00a1dd4b5638b102090e37a5e1c', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_CLIENT_SECRET', '96ef1c4020a914c65153e800ab6babf7e114c971dc4375613bccacdbbca682b7', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_MERCHANT_KEY', '8366746', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_SUCCESS_URL', 'http://localhost:4201/paymentSuccess.html', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_FAILURE_URL', 'http://localhost:4201/paymentFailure.html', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_PAYMENT_LINK_URL', 'https://uatoneapi.payu.in/payment-links/', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_AUTH_TOKEN_URL', 'https://uat-accounts.payu.in/oauth/token', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_PAYMENT_LINK_SALT', 'MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDIjbqe8VOKt6+l1IGlKm7v3L0yQj5qyK9vmgjByirzDMuq7TY9MepnNK8GX/45E9x1rGRBkdniwotvd6mG/lwWr7qplWF6k0U1XydhhS6XDA8KJHrGyWbanYOSFBM2aFfR3xm3Cag8GGo7Wf2GGo+jR47HwDQNuljmJDL8tt4M1SUf3zv4M2ZYTsV4Q4GMbzx27oBAuajXVOpI9zksxgK1U2003oLzyIHI2X9sZ+aQyGulanhJAGFEP5CRprhl3inuD+GxYvDAr7h/h2esZrkYwbpV7pzm0d6MsWjb8jS+Mdfv6xEJEgrdgWqlKz8PL6PRurUQuzxQZ3ZibsR9XCEZAgMBAAECggEBAKJlgZRqvcc57lVNup1Puv0ntVwZIAm/769i/2xMlHKzDciexKjQ3oorsv5qhKUbXA09RwGBZPmlptrf/rp9BXypV3EysPhcgFIzduQUwCE3doYz++cz9sFXgs6qytPnshUTnvMEMuJWss7Uk+IfZ+hDbW56SKxFFO9mqJLSUQP5tTrqbQS5o+8V1mEO4psB133Z40PxDMH2Vlb+4V/kZQrAOkM+7EaPZyQ56bgDk48cUY5bcSlV5Hvj+hfFppad2lZrCMA2IE1qmFTa5B1lYfQ1LNljWz18Fn/yEAe3Y+49sRRuSRomiTetvRVXiu/lIn4N7yXkxONGtthjVZcWfbECgYEA6LmqZUo/gA0EeMAqGOskhEuu2ZPtKh5DrQ0vR3YG1RVOe2akOovKnSnqJIAk6hdKsXDSN8KkFB5yOFn/xBoNC/tckcFYfKDeT1Ejm8X4xJH8wJ1Xz6ZF5VhycZyPzZXvH580kn1QwSir6QFl2jUcIRfnQT9LCkdz6G/yeQBuNQMCgYEA3JxkJxcLkpWmAam8p4uP+uBucEBfggIMll/lsmPrejIaaoYc4MeQ/13r8x7UibpEtFiJ0irTLNtbHzzOMOJho6/y72dGtmiC6AQ2aFnGFUiLRnzKLxkb5wH/GJ7/tKRiSqBojOZozK1LV+gsH98S0uKnfYZbob1BTp9AWhpJsLMCgYBf72mWp6zVlIgrXsv7ybMb9jb16kzx99feFnlO9BCM9mOL6xv4Jng3oXer5EdTWJap3uBJrCsHQCXFpRQSOsr2DYBzdUiM16PM/p23MT0Di0qt/C6eouRdTsCepPOCxDP2zSJW4OHRPnrfanwaH5qg6cGi2/yLGDlnr/48czj7EQKBgDXbG/wK/TFAxTD8vehtAq4tIejXXjl/xJDlQk7lY7QGDTraGKyrK3Ob+yDz8tDYnzMjtimPeNQnjLw4fOGkMk/igTz+il23b9WySZH58Ih7mDyR9E3BJHWloB/jmOsBgllThZJ4ITduSslgDX2OWKsQ1/Cl3zLB3/KQoAYfCsRPAoGAG+J3GsG145Ulxl0m7wmFxjmqE9eE1mF0udtVzuPIcjbIL+NPNeSkyiFH/vJ6Vja8mH0o74i3zz4bTm9YzyOE6ooCC1xXUFI76VzmFA05qnppjR+c3CneToof2I1YhQjnrY5PiaXrfC3oPaZf0X8f4UnByiilFbZQJSaDfFW8FkA=', CURRENT_TIMESTAMP, '0');
