<?php

use list4\accountApp\classes\AccountController;
use list4\accountApp\classes\AccountRepositoryJson;
use Slim\App;

require __DIR__ . '/../../../../vendor/autoload.php';

$config['displayErrorDetails'] = true;

$app = new App(["settings" => $config]);
$container = $app->getContainer();

$container['AccountController'] = function () {
    return new AccountController(new AccountRepositoryJson());
};

//was not working with AccountController::class . home
$app->get("/accounts/{id}", "AccountController:getAccount");
$app->get("/accounts", "AccountController:getAllAccounts");
$app->post("/accounts", "AccountController:createAccount");
$app->put("/accounts/{id}", "AccountController:updateAccount");

$app->run();
