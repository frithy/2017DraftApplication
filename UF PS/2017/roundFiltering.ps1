$additionalCode = @'

    public class round{
        public int roundNumber=0;
        public int Ruck=0;
        public int Midfielder=0;
        public int Back=0;
        public int Forward=0;

        public round(){}
        
    }

'@

$dpicks = Import-Csv .\FinalPicks_Round.csv
Add-Type $additionalCode -Language CSharp

$rounds = @();

foreach($pick in $dPicks){
    
    $found=$false
    foreach($round in $rounds){
    
    
        if($round.roundNumber -eq $pick.round){
            $found = $true

            if($pick.position -like "*Mid*"){
            
                $round.midfielder++; 
            }
            if($pick.position -like "*Ruc*"){
                $round.Ruck++; 
            }
            if($pick.position -like "*Bac*"){
                $round.back++; 
            }
            if($pick.position -like "*for*"){
                $round.forward++; 
            
            }

        
        } 
    
    }

    if(!$found){
        $newRound = new-object round
        
         if($pick.position -like "*Mid*"){
            
                $newround.midfielder++; 
            }
            if($pick.position -like "*Ruc*"){
                $newround.Ruck++; 
            }
            if($pick.position -like "*Bac*"){
                $newround.back++; 
            }
            if($pick.position -like "*for*"){
                $newround.forward++; 
            
            }
            $newRound.roundNumber = $pick.Round
            $rounds+=$newRound
            

    }
    

}
