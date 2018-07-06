echo "compiling..."
cd src/
javac -Xlint:unchecked -cp lib/*.jar extras/*.java interfaces/*.java registry/*.java clientSide/*.java serverSide/*.java
echo "step 1/4 done!"

echo "Deploying local..."
cp -a registry/Register.class dir_registry/interfaces/
cp -a registry/*.class dir_registry/registry/

cp -a interfaces/*.class dir_serverSide/interfaces/
cp -a serverSide/*.class dir_serverSide/serverSide/


cp -a interfaces/*.class dir_clientSide/interfaces/
cp -a clientSide/*.class dir_clientSide/clientSide/
echo "step 2/4 done!"

echo "creating Public folders..."
mkdir -p ~/Public/classes
mkdir -p ~/Public/classes/interfaces
mkdir -p ~/Public/classes/clientSide
echo "step 3/4 done!"

echo "Deploying for Public..."
cp interfaces/*.class ~/Public/classes/interfaces
cp registry/*.class ~/Public/classes/interfaces
cp clientSide/*.class ~/Public/classes/clientSide
echo "step 4/4 done!"
echo "Finish!"
