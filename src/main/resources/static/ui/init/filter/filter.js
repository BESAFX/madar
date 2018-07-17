app.filter('hijri', function () {
    return function (item) {
        if (item) {
            return new HijriDate(new Date(item).getTime()).getDateString();
        } else {
            return "---";
        }
    };
});

app.filter('hijriYearShortcut', function ($log) {
    return function (item) {
        if (item) {
            var value = new HijriDate(new Date(item).getTime()).getFullYearString().slice(-2);
            return value;
        } else {
            return "---";
        }
    };
});

app.filter('hijriWithTime', function ($log) {
    return function (item) {
        if (item) {
            var date = new Date(item);
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var ampm = hours >= 12 ? 'مساءاً' : 'صباحاَ';
            hours = hours % 12;
            hours = hours ? hours : 12; // the hour '0' should be '12'
            minutes = minutes < 10 ? '0' + minutes : minutes;
            var strTime = hours + ':' + minutes + ' ' + ampm;
            var dateString = new HijriDate(new Date(item).getTime()).getDateString();
            return dateString + " - " + strTime;
        } else {
            return "---";
        }
    };
});

app.filter('propsFilter', function () {
    return function (items, props) {
        var out = [];

        if (angular.isArray(items)) {
            var keys = Object.keys(props);

            items.forEach(function (item) {
                var itemMatches = false;

                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }

        return out;
    };
});

app.filter('sumByKey', function () {
    return function (input) {
        var i = input instanceof Array ? input.length : 0;
        var a = arguments.length;
        if (a === 1 || i === 0)
            return i;

        var keys = [];
        while (a-- > 1) {
            var key = arguments[a].split('.');
            var property = getNestedPropertyByKey(input[0], key);
            if (isNaN(property))
                throw 'filter sumProduct can count only numeric values';
            keys.push(key);
        }

        var total = 0;
        while (i--) {
            var product = 1;
            for (var k = 0; k < keys.length; k++)
                product *= getNestedPropertyByKey(input[i], keys[k]);
            total += product;
        }
        return total;

        function getNestedPropertyByKey(data, key) {
            for (var j = 0; j < key.length; j++)
                data = data[key[j]];
            return data;
        }
    }
});