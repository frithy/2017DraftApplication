$players = import-csv "C:\Users\Andrew\YoungerPlayers.csv"


$foundCount =0;
$notFound = 0;

foreach($player in $players){

$name = $player.name.Split("�")[0] 
$position = $player.name.Split("�")[1].tolower().split("-")[1].replace(")","").replace("c","MID").replace("f","FWD").replace("r","RUC").replace("b","DEF").trim()
$player.Name = $name.Trim()
$player.Position= $position

  




}