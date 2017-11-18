$code = @'

public class player{

    public string Name="";
    public string Club="";
    public string Position="";
    public string age="";
    public int ID=-1;


    public player(){}

}
'@

add-type -Language CSharp -TypeDefinition $code 

$playerPages = gci
$outarray = @();

foreach($page in $playerPages){


    $content = (Get-Content $page.fullname)


    $name = ($content | where-object{$_ -like "*<h3 style=`"margin-top: 5px*"}).trim().split(">")[1].split("<")[0]  
    $club = ($content | where-object{$_ -like "*<p>*</p>*"})[1].trim().replace("<p>","").replace("</p>",""); 
    $posAge = ($content | where-object{$_ -like "*<p>*</p>*"})[2].Trim().split(">")
    $position = $posAge[3].replace("&nbsp; <em","").trim(); 
    $age = $posAge[5].replace("</p","").trim() 
    $ID = $page.Name.replace(".txt","")
    
    
    $outobj = new-object player

    $outobj.Club = $club
    $outobj.age = $age
    $outobj.ID = $ID
    $outobj.Position = $position
    $outobj.Name = $name 

    $outarray += $outobj
     

    
    }









    $outarray | where-object{$_.position -ne "" -and $_.age -ne ""} | Export-Csv playerData.csv
