<?php

use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Tools\Setup;

require_once "vendor/autoload.php";

$isDevMode = true;
$config = Setup::createXMLMetadataConfiguration(array("doctrine/config/xml"), $isDevMode);

// database configuration parameters
$conn = array(
    'driver' => 'pdo_pgsql',
    'charset' => 'utf8',
    'user' => 'stachu',
    'password' => 'password',
    'dbname' => 'list9',
    'host' => '127.0.0.1',
    'port' => '5432'
);

// obtaining the entity manager
$entityManager = EntityManager::create($conn, $config);