document.addEventListener('DOMContentLoaded', function() {
let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);  //3ヶ月まで表示
flatpickr('#fromRentalStartTimeDateToRentalEndTimeDate', {
mode: "range",
locale: 'ja',
enableTime: true, //日時
dateFormat: "Y-m-d\\TH:i",
time_24hr: true,
minDate: 'today',
maxDate: maxDate,
onChange: function(selectedDates, dateStr, instance) {//イベントハンドラー
            const input = document.querySelector('input[name="fromRentalStartTimeDateToRentalEndTimeDate"]');
            if (input) input.value = dateStr;
        }
    });
});