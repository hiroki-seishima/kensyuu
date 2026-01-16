const stripe = Stripe("pk_test_51Sq26A6fHhDNGpxcI6pRcRF5Is7kBNXNeCw8dI2iPZJgCAUHTP6YNoSFV3SULZvfojLWmhc3flTsAi75L399HOjC00aHOak8rm");
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
    stripe.redirectToCheckout({
        sessionId: sessionId
    })
});