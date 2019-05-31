<?php

use Acelaya\Doctrine\Type\PhpEnumType;
use Doctrine\DBAL\Types\Type;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Tools\Setup;
use list9\Status;

require_once "vendor/autoload.php";

$isDevMode = true;
$config = Setup::createXMLMetadataConfiguration(array("doctrine/config/xml", "vendor/michaelgooden/mdg-money-doctrine/config/orm/"), $isDevMode);

// database configuration parameters
$conn = array(
    'driver' => 'pdo_pgsql',
    'charset' => 'utf8',
    'user' => 'stachu',
    'password' => 'password',
    'dbname' => 'phpdb',
    'host' => '127.0.0.1',
    'port' => '5432'
);

// obtaining the entity manager
$entityManager = EntityManager::create($conn, $config);

PhpEnumType::registerEnumType(Status::class);
$platform = $entityManager->getConnection()->getDatabasePlatform();
$platform->registerDoctrineTypeMapping('string', Status::class);

Type::addType('uuid', 'Ramsey\Uuid\Doctrine\UuidType');
//should be uuid type because string can lead to performance problems
$platform->registerDoctrineTypeMapping('string', 'uuid');

Type::addType('currency', 'Mdg\Money\Doctrine\CurrencyType');
$platform->registerDoctrineTypeMapping('string', 'currency');
