<?php

use list4\accountsViewApp\classes\AccountsController;
use list4\accountsViewApp\classes\AccountServiceHttp;
use Slim\App;
use Slim\Container;
use Slim\Http\Environment;
use Slim\Http\Uri;
use Slim\Views\Twig;
use Slim\Views\TwigExtension;

require __DIR__ . '/../../../../vendor/autoload.php';

$config['displayErrorDetails'] = true;

$app = new App(["settings" => $config]);
$container = $app->getContainer();

$container['view'] = function (Container $container) {
    $view = new Twig('../templates');

    // Instantiate and add Slim specific extension
    $router = $container->get('router');
    $uri = Uri::createFromEnvironment(new Environment($_SERVER));
    $view->addExtension(new TwigExtension($router, $uri));

    return $view;
};

$container['serverAddress'] = function (Container $c) {
    return 'localhost:8080';
};

$container['AccountsController'] = function (Container $container) {
    return new AccountsController($container->get('view'), new AccountServiceHttp($container->get('serverAddress')));
};

$app->get("/accounts", "AccountsController:showAccounts");
$app->run();
