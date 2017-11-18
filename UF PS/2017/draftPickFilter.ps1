$picks = get-content .\draftPicks.txt | where-object{$_ -ne ""}

$count =0;


$code = @'
    
    public class draftPick{
    
        public string drafter = "";
        public int drafterPick=0;
        public string position="";
        public int pickTaken=0;
        
        public draftPick(){}
    }


'@

add-type $code -Language CSharp
$draftPicks = @();
$pick=0
foreach($line in $picks){
    
    if($line -like ">*"){
        write $line 
        $pick= [int]::Parse($line.replace(">","").split(";")[1])
        $currentPick = $line.replace(">","").split(";")[0]
        
    }elseif($line -notlike "*player*" -and $line -ne ";"){
        $x=$null
        $x=$line.split(";")[3].split(" ")[0]
        
        if($x -ne $null){
        $count++;

        $outobj = new-object draftPick
        $outobj.position= $line.split(";")[3].split(" ")[0]
        $outobj.drafterPick = $pick;
        $outobj.drafter = $currentPick
        $outobj.pickTaken = [Int]::Parse($line.split(";")[1].replace("(","").replace(")","").trim())

        $draftPicks+=$outobj
        
         }
    }

}