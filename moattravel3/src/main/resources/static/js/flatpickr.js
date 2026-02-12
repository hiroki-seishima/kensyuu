let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

flatpickr('#fromCheckinDateToCheckoutDate', {
    mode: "range",
    locale: 'ja',
    minDate: 'today',
    maxDate: maxDate,
    onChange: function (selectedDates, dateStr, instance) {  //Flatpickrのmode:"range"はfrom-to形式ではなく"YYYY-MM-DD to YYYY-MM-DD"を出力。あなたのsplit("から")は日本語「から」を期待しているのでnull or 形式不一致 で失敗。
        if (selectedDates.length === 2) {
            let start = instance.formatDate(selectedDates[0], "Y-m-d");
            let end = instance.formatDate(selectedDates[1], "Y-m-d");
            document.querySelector('#fromCheckinDateToCheckoutDate').value = start + "から" + end;
        }
    }
});