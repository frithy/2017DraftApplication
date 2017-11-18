
$links = get-content "C:\Users\Andrew\Documents\Links.txt"

$code = @'
    
    public class linkedData{
        
        public string age = "0";
        public string linkName = "";
        public string gamesplayed = "0";
        public string club = "";

        public linkedData(){}

    
    } 


'@


Add-Type -TypeDefinition $code -Language CSharp
$linkObjs =@();

foreach($link in $links){

    if($link.split("`"")[1] -like "pp*"){
        
        $html = Invoke-WebRequest ("www.footywire.com/afl/footy/"+$link.split("`"")[1]);

        Out-File -FilePath ("C:\Users\Andrew\Supercoach\RawHTML\" +$link.split("`"")[1]+".txt") -InputObject $html.RawContent;
        #$html.RawContent >> $outfile 

            $linkobj = New-Object linkedData
            $linkobj.linkname = $link.split("`"")[1]
            $passed = $false
            $linkObj.club = $link.split("=").Replace("--","@").split("@").replace('"',"")[1].replace("pp","").replace("-"," ").trim();
            $i = 0;

            foreach($line in (get-content ("C:\Users\Andrew\Supercoach\RawHTML\" +$link.split("`"")[1]+".txt"))){
                    
                    if($line -like '<th title="Games">GM</th>'){
                        $passed=$true;
                    
                    }
                    if($line -like "*Age:*"){
                    
                        write $line.Replace("Age: ","").split(" ")[0];
                        $linkobj.age = ($line.Replace("Age: ","").split(" ")[0]+" "+$line.Replace("Age: ","").split(" ")[1])
                    }

                if($line -like '<td class="drow">*</td>' -and $passed -and $i -lt 1){
                    $i++; 
                    
                    write $line.Replace('<td class="drow">','').Replace("</td>","")
                    $linkobj.gamesplayed = $line.Replace('<td class="drow">','').Replace("</td>","")
                      write $link
                      break;
                
                }
            
            
            }
            $linkObjs += $linkobj 
                
                
    }

}

