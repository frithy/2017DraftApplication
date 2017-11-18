

Foreach($player in $players){


    if($player.Age -eq ""){
         
         foreach($fwLink in $linkObjs){
         
         if($fwlink.linkName -like ("*"+$player.name.trim().replace(" ","*")+"*")){
         
            $years = [double]$fwlink.age.split(" ")[0].replace("yr","")
            if($flink.age -like "*mth*"){
                
                $month = [double]$fwlink.age.split(" ")[1].replace("mth","")
            
            }
            $age = (($years * 12) + $month)/12
            $player.Age = $age 
         }
            
         
         
         }


    }


}


$newPlayerArray = $players;

foreach($player in $newPlayerArray){
    $foundcount= 0;
    foreach($fwLink in $linkObjs){
         
         if($fwlink.linkName -like ("*"+$player.name.trim().replace(" ","*")+"*")){
            
            $foundcount++;
            $club =  $fwLink.club;
               
         
         }

         if($foundcount -eq 1){
         
            $player.Club = $club
         
         }elseif($foundcount -eq 0){
         
            $player.Club = "notfound"
         }else{
         
            $player.Club = "multiple"
         }


}
}
#if($link.linkname-like ("*"+$player.player.trim().replace(" ","*")+"*")