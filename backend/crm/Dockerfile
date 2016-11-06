FROM php:7.0-apache
COPY EspoCRM-4.2.7/ /var/www/html/
RUN cd /var/www/html && find data -type d -exec chmod 775 {} + && chown -R 33:33 .;

RUN apt-get update && apt-get install -y \
        libfreetype6-dev \
        libjpeg62-turbo-dev \
        libmcrypt-dev \
        libpng12-dev \
    && docker-php-ext-install -j$(nproc) pdo_mysql mcrypt \
    && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install -j$(nproc) gd

RUN a2enmod rewrite
RUN service apache2 restart
