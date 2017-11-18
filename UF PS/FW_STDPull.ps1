function get-standarddeviation {


param(
$numbers
)

$avg = ($numbers | measure-object -Average).Average 
$popdev = 0

foreach($number in $numbers){
    $popdev += [Math]::pow(($number - $avg), 2)

   

}
$sd = [Math]::Sqrt($popdev / ($numbers.count - 1))
$sd 

}


$playerDB = Import-Csv -Path "C:\Users\Andrew\updatedlinks.csv"


foreach($player in $playerDB){


$url = ("http://www.footywire.com/afl/footy/"+ $player.Link.replace("pp-","pg-"))

$path = ($player.Link.replace("pp-","pg-").replace("-","")+".txt") 

$body = (Invoke-WebRequest $url).rawcontent | Out-File -FilePath "C:\Users\Andrew\UFSTD\$path"


$html = Get-Content "C:\Users\Andrew\UFSTD\$path"





$i = 0;
$scores = @();
foreach($line in $html){

    if($line -like "<tr bgcolor=* onMouseOver=`"this.bgColor=*;`" onMouseOut=`"this.bgColor=*;`">"){
       $I++
       
    }

    elseif($I -gt 0){
        $i++; 
        if($I -eq 19){
            $scores += [double]$line.replace("<td align=`"center`">","").replace("</td>","") 
            
            $i = 0;
           
        } 
    
    
    }

    


}

 $avg = ($scores | measure-object -Average).Average 

 $player.GamesRemoved = 0;
 $player.RemoveFromAVG=0;
 



 foreach($Score in $scores){
     
     if($score -lt ($avg/2)){
     
        $player.GamesRemoved++;
        $player.RemoveFromAVG+=$score; 
        
     
     }
 
 
 }

 #$player.Std = get-standarddeviation($scores)
 
 
 
 }
