
echo "Please Enter the command as follows:"
echo "./tweets-k-means <numberOfClusters> <initialSeedsFile> <TweetsDataFile> <outputFile>"
read arg0 arg1 arg2 arg3 arg4

arg2=$PWD"/"$arg2
arg3=$PWD"/"$arg3
arg4=$PWD"/"$arg4

echo "starting java program!"

cd ./bin/ && java part2.Launcher "$arg1" "$arg2" "$arg3" "$arg4"

