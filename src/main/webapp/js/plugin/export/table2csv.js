/**
 * Created by wangrenhui on 15/3/18.
 */

$.fn.table2csv = function (selector, filename) {

  var csv = '"',

  // Temporary delimiter characters unlikely to be typed by keyboard
  // This is to avoid accidentally splitting the actual contents
    tmpColDelim = String.fromCharCode(11), // vertical tab character
    tmpRowDelim = String.fromCharCode(0), // null character

  // actual delimiter characters for CSV format
    colDelim = '","',
    rowDelim = '"\r\n"',
    $table = $(selector);

  $table.each(function () {
    var $rows = $(this).find('tr');

    // Grab text from table into CSV formatted string
    csv = csv.length > 1 ? csv + rowDelim : csv;
    csv = csv + $rows.map(function (i, row) {
      var $row = $(row);
      if ($row.attr("csv") != "false") {
        var $cols = $row.find('td');
        $cols = $cols.length > 0 ? $cols : $row.find('th');

        return $cols.map(function (j, col) {
          var $col = $(col);
          if ($col.attr("csv") != "false") {
            var text = $col.text();
            return text.replace('"', '""'); // escape double quotes
          }
        }).get().join(tmpColDelim);
      }
    }).get().join(tmpRowDelim)
      .split(tmpRowDelim).join(rowDelim)
      .split(tmpColDelim).join(colDelim);

  });
  csv = csv + '"';
  // Data URI
  var csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);

  $(this).attr({
    'download': filename,
    'href': csvData,
    'target': '_blank'
  });
};

