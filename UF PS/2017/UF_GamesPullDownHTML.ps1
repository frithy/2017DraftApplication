$2014 = 'C:\Users\Andrew\2017UF_HTML\2014Games\'
$2013 = 'C:\Users\Andrew\2017UF_HTML\2013Games\'
$2012 = 'C:\Users\Andrew\2017UF_HTML\2012Games\'
$i =1;


while($I -lt 1864){

    $2014 = Invoke-WebRequest "http://ultimate-footy.theage.com.au/players/$I/gamelog/2014"
    $2013 = Invoke-WebRequest "http://ultimate-footy.theage.com.au/players/$i/gamelog/2013"
    $2012 = Invoke-WebRequest "http://ultimate-footy.theage.com.au/players/$i/gamelog/2012"
    $2014.RawContent >> ("C:\Users\Andrew\2017UF_HTML\2014Games\$I.html");
    $2013.RawContent >> ("C:\Users\Andrew\2017UF_HTML\2013Games\$I.html");
    $2012.RawContent >> ("C:\Users\Andrew\2017UF_HTML\2012Games\$I.html");
    
    $I++;

    write $I; 


}

