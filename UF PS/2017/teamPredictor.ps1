$teams = import-csv teams.csv
$players = import-csv "C:\Users\Andrew\Desktop\players2016.csv"
$picks = import-csv "C:\Users\Andrew\Desktop\FinalPicks_Round.csv"
$forwards = 6
$defenders =6;
$midfielders = 8;
$rucks = 2;

$i = 0;

$mypick = 5

foreach($team in $teams){

    $team.Def = [int]::Parse($team.def)
    $team.mids = [int]::Parse($team.mids)
    $team.Fwd = [int]::Parse($team.Fwd)
    $team.rucks = [int]::Parse($team.Rucks)
    $team.ProjectedScore = [int]::Parse($team.ProjectedScore)

    

}

while($i -lt 308){
    $myPick = $false;
    $position = "";
    
    foreach($pick in $picks){
         
         if($pick.pickTaken -eq $i){
            
            $position = $pick.position
            
            if($pick.drafterPick -eq 55){
            
            
            }else{
                
                

                foreach($team in $teams){
                 if($team.pick -eq $pick.drafterPick){
                    $drafted = getBestPlayer -SearchPosition $position -team $team -players $players
                    
                foreach($player in $players){
                    if($player.id -eq $drafted.id){
                    
                        $player.taken = $true;
                        $team.ProjectedScore += $player.total 
                                   if($drafted.position -like "*Mid*"){
                                        $team.mids++; 
                                    }
                                    if($drafted.position -like "*Ruc*"){
                                        $team.Rucks++; 
                                    }
                                    if($drafted.position -like "*Bac*"){
                                        $team.def++; 
                                    }
                                    if($drafted.position -like "*for*"){
                                        $team.fwd++; 
            
                                    }
                                   
                            }
                        
                        }

                    }
                
                }

            }

            
         }
    
    }
    




    $i++;
}


function getBestPlayer(){
    Param($SearchPosition, 
          $players) 
    
    $matching = $players | Where-Object{$_.position -like $SearchPosition} | Where-Object{$_.taken -eq $false}
    $highestTotal = 0;
    $drafted = $null;
    foreach($player in $matching){
        if([int]::Parse($player.total) -gt $highestTotal){
            $highestTotal = [int]::Parse($player.total)
            $Drafted = $player 
        }
    
    }


    return $drafted


}


