--liquibase formatted sql
--changeset {narendra}:{id}


INSERT INTO config
(name, value, created_at, deleted)
VALUES('RAZOR_CLIENT_KEY', 'rzp_test_Ra0R3CcS5rSZI6', CURRENT_TIMESTAMP, '0');
INSERT INTO config
(name, value, created_at, deleted)
VALUES('RAZOR_CLIENT_SECRET', 'kCd1zbOoxLwDas7oISbY8rBe', CURRENT_TIMESTAMP, '0');
INSERT INTO config
(name, value, created_at, deleted)
VALUES('RAZOR_FAILURE_URL', 'http://3.110.16.23/MahaExam/assets/paymentFailure.html', CURRENT_TIMESTAMP, '0');
INSERT INTO config
(name, value, created_at, deleted)
VALUES('RAZOR_INVOICE_PREFIX', 'RINV', CURRENT_TIMESTAMP, '0');
INSERT INTO config
(name, value, created_at, deleted)
VALUES('RAZOR_MERCHANT_KEY', 'RXzje03MLDfBIt', CURRENT_TIMESTAMP, '0');
INSERT INTO config
(name, value, created_at, deleted)
VALUES('RAZOR_PAYMENT_LINK_SALT', 'Payments@MahaExam2025', CURRENT_TIMESTAMP, '0');
INSERT INTO config
(name, value, created_at, deleted)
VALUES('RAZOR_PAYMENT_LINK_URL', 'https://api.razorpay.com/v1/payment_links', CURRENT_TIMESTAMP, '0');
INSERT INTO config
(name, value, created_at, deleted)
VALUES('RAZOR_SUCCESS_URL', 'http://3.110.16.23/MahaExam/assets/paymentSuccess.html', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('CONFIGURED_PAYMENT_GATEWAY', 'RAZOR_PAY', CURRENT_TIMESTAMP, '0');

