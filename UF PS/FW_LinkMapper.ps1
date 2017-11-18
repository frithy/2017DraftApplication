
$links = get-content "C:\Users\Andrew\Documents\Links.txt"
$2016DB = import-csv "C:\Users\Andrew\Desktop\playerDB_WithTOG.csv"
 
 $count =0;
 foreach($player in $2016DB){
      
      if($player.Link -eq "NA"){
        
        foreach($link in $links){
            
           
           if($link -like "*&nbsp;<a href=*</a>*"){
           
               $nameChars = $link.Split("><")[2].trim().split(",");
              
               #write $nameChars.count 
               #write "$lastname,$firstname"
               $match = $true;

               foreach($char in $nameChars){
                    $char = $char.trim();
                    if(!($player.Player.ToLower() -like "*$char*")){
                        
                        $match = $false;
                        
                        
                       
                    }
               
               }

               if($match){
                write $player.Player
                $player.Link = $link.split("`"")[1];
                $count++;
               }
               

           }   }                


}
}