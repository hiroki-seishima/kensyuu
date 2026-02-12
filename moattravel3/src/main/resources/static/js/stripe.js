const stripe = Stripe('公開可能キー');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
stripe.redirectToCheckout({
sessionId: sessionId
})
});