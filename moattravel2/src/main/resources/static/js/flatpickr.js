let maxDate = new Date();  //33-5

maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

flatpickr('#fromCheckinDateToCheckoutDate', {
  mode: "range",
  locale: 'ja',
  minDate: 'today',
  maxDate: maxDate
});