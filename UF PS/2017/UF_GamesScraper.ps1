$code =@'
    
    public class Games {

        public int ID = -1;
        public string year = "2013";
        public string Rd="";
        public string Opp="";
        public string Points="";
        public string TOG = "";
        public string KI="";
        public string SCOR="";
        public string HB="";
        public string MA="";
        public string HO="";
        public string TA="";
        public string FF="";
        public string FA="";
        public string FD="";
        public string GL="";
        public string BH="";
        public string D="";
        public string ED="";
        public string IED="";
        public string CP="";
        public string Dperc="";
        public string R50="";
        public string CL = "";
        public string SP="";

        public Games(){}

       }
'@

add-type -Language CSharp -TypeDefinition $code

$playerRecords = gci 
$gameArray = @();
$game = $null 
foreach($record in $playerRecords){
    
    $inBody =$false 
    $inRow = $false
    $count = 0;
                
    if($record.Length -gt 8710){
    
        $content = get-content $record.FullName 
        

        foreach($line in $content){
        
            if($line -like "*<tbody>*"){
                $inBody = $true 
                            
            }
            if($line -like "*</tbody>*"){
                $inBody = $false 
            
            }
            if($inbody){
                if($line -like "*<tr>*"){
                    $inRow=$true
                    $game = New-Object Games
                    $game.ID = $record.name.replace(".html","")
                
                }elseif($line -like "*</tr>*"){
                    $gameArray += $game
                    $inrow = $false; 
                    $count = 0;
                }

                if($inRow){
                
                    $count++; 
                    write ("$count $line")



                    Switch ($count){
                        2 {$game.Rd = $line.replace("<td>","").replace("</td>","").trim();}
                        5 {$game.Opp = $line.split(">")[1].split("<")[0].trim();}
                        8 {$game.TOG = $line.split(">")[1].split("<")[0]}
                        9 {$game.SCOR = $line.split(">")[1].split("<")[0]}
                        11{$game.KI = $line.split(">")[1].split("<")[0]}
                        12{$game.HB = $line.split(">")[1].split("<")[0]}
                        13{$game.MA = $line.split(">")[1].split("<")[0]}
                        14{$game.HO = $line.split(">")[1].split("<")[0]}
                        15{$game.ta = $line.split(">")[1].split("<")[0]}
                        16{$game.ff = $line.split(">")[1].split("<")[0]}
                        17{$game.fa  = $line.split(">")[1].split("<")[0]}
                        18{$game.FD = $line.split(">")[1].split("<")[0]}
                        19{$game.GL = $line.split(">")[1].split("<")[0]}
                        20{$game.BH = $line.split(">")[1].split("<")[0]}
                        21{$game.D = $line.split(">")[1].split("<")[0]}
                        22{$game.ED = $line.split(">")[1].split("<")[0]}
                        23{$game.IED = $line.split(">")[1].split("<")[0]}
                        24{$game.CP = $line.split(">")[1].split("<")[0]}
                        25{$game.Dperc = $line.split(">")[1].split("<")[0]}
                        26{$game.R50 = $line.split(">")[1].split("<")[0]}
                        27{$game.CL  = $line.split(">")[1].split("<")[0]}
                        28{$game.SP  = $line.split(">")[1].split("<")[0]}
                        
                    } 

                }



            
            }


        }
    
    
    }
    
    





}

$gameArray | export-csv GameData.csv