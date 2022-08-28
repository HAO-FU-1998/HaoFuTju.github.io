inputfile = input ('Please Input File Name: ', 's');
load (inputfile);
img = testima;
method = input('Select Processing Method (0 for Fminsearch, 1 for Expectation Maximization): ');
disp('Example: [0.3 20 60 0.4 20 140 0.3 20 200]');
param = input('Input the initial parameters: ');

histo = imhist(img);
L = 0:255;
prob = histo / sum(histo);

p1 = param(1); % Input parameter
m1 = param(3);
sigma1 = param(2);
p2 = param(4);
m2 = param(6);
sigma2 = param(5);
p3 = param(7);
m3 = param(9);
sigma3 = param(8);

y = p1 * (1 / (sqrt(2 * pi) * sigma1)) * exp(-(L - m1).^2 / (2 * sigma1^2)) + p2 * (1 / (sqrt(2 * pi) * sigma2)) * exp(-(L - m2).^2 / (2 * sigma2^2)) + p3 * (1 / (sqrt(2 * pi) * sigma3)) * exp(-(L - m3).^2 / (2 * sigma3^2));
y = y ./ sum(y); % Initial Gaussian distribution

if (method == 0) % Using Fminsearch
    figure('Name','Fminsearch','NumberTitle','off');
    set(gcf,'outerposition',get(0,'screensize'));
    subplot(3,2,1), imagesc(img); % Plot original image
    title('Original Image');
    pause;
    
    subplot(3,2,2), plot(L, prob); % Plot histogram of the image
    title('Target Histogram');
    pause;
    
    subplot(3,2,3), plot(y); % Plot the given initial distribution
    title('Initial Distribution');
    pause;
    % Using fminsearch with termination condition: the maximum number of iterations, function value termination tolerance, the termination tolerance of the current point x
    optimal = fminsearch(@CurveFunction, param, optimset('MaxIter',10000, 'TolFun',1e-2, 'TolX',1e-2)); 
        
    finish = optimal(1) * (1 / (sqrt(2 * pi) * optimal(2))) * exp(-(L - optimal(3)).^2 / (2 * optimal(2) ^2)) + optimal(4) * (1 / (sqrt(2 * pi) * optimal(5))) * exp(-(L - optimal(6)).^2 / (2 * optimal(5) ^2)) + optimal(7) * (1 / (sqrt(2 * pi) * optimal(8))) * exp(-(L - optimal(9)).^2 / (2 * optimal(8) ^2));
    finish = finish / sum(finish); % Fit the curve with optimal parameters
    
    subplot(3,2,[5,6]), plot(L, prob, 'b', L, finish, 'r--o'); % Plot the final comparison of Initial Distribution and Target Histogram
    title('Final Solution');
    
end

if (method == 1) % Using Expectation Maximization
    histo = imhist(img);
    L = 0:255;
    prob = (histo / sum(histo))';
    
    figure('Name','Expectation Minimization','NumberTitle','off');
    set(gcf,'outerposition',get(0,'screensize'));
    subplot(3,2,1), imagesc(img); % Plot original image
    title('Original Image');
    pause;
    
    subplot(3,2,2), plot(L,prob); % Plot histogram of the image
    title('Target Histogram');
    hold on;
    pause;
    
    subplot(3,2,3), plot(y); % Plot the given initial distribution
    title('Initial Distribution');
    pause;
    
    for j = 1:length(histo)
        a = (1 / (sqrt(2 * pi) * sigma1)) * exp(-(L - m1) .^ 2 / (2 * sigma1 ^2)); % Normal probability density function
        b = (1 / (sqrt(2 * pi) * sigma2)) * exp(-(L - m2) .^ 2 / (2 * sigma2 ^2));
        c = (1 / (sqrt(2 * pi) * sigma3)) * exp(-(L - m3) .^ 2 / (2 * sigma3 ^2));
        
        w1 = a * p1./ (a * p1 + b * p2 + c * p3); % Occurance probability
        w2 = b * p2./ (a * p1 + b * p2 + c * p3);
        w3 = c * p3./ (a * p1 + b * p2 + c * p3);
        
        p1 = prob * w1'; % The ratio
        p2 = prob * w2';
        p3 = prob * w3';
        
        m1 = ((prob .* L) * w1') / p1; % Mean level
        m2 = ((prob .* L) * w2') / p2;
        m3 = ((prob .* L) * w3') / p3;
        
        sigma1 = sqrt((prob .* ((L - m1) .^ 2) * w1') / p1); % Standard deviation
        sigma2 = sqrt((prob .* ((L - m2) .^ 2) * w2') / p2);
        sigma3 = sqrt((prob .* ((L - m3) .^ 2) * w3') / p3);
        
        fem = p1 * (1 / (sqrt(2 * pi) * sigma1)) * exp(-(L - m1) .^ 2 / (2 * sigma1 ^2)) + p2 * (1 / (sqrt(2 * pi) * sigma2)) * exp(-(L - m2) .^ 2 / (2 * sigma2 ^2)) + p3 * (1 / (sqrt(2 * pi) * sigma3)) * exp(-(L - m3) .^ 2 / (2 * sigma3 ^2));
        fem = fem / sum(fem); % Compute the curve
        
        subplot(3,2,4), plot(L, prob, 'b', L, fem, 'r'); % Plot the real-time progress
        pause(0.01);
        title('Work in Progress');
    end
    
    subplot(3,2,[5,6]), plot(L, prob, 'b', L, fem, 'r--o'); % Plot the final comparison of Initial Distribution and Target Histogram
    title('Final Solution');
end

function cf = CurveFunction(param) % Function for fminsearch in method 0
    histo = evalin('base', 'histo'); % Evaluate the histogram in the base
    LF = 0:255;
    prob = (histo / sum(histo))';
    
    fcf = param(1) * (1 / (sqrt(2 * pi) * param(2))) * exp(-(LF - param(3)).^2 / (2 * param(2)^2)) + param(4) * (1 / (sqrt(2 * pi) * param(5))) * exp(-(LF - param(6)).^2 / (2 * param(5)^2)) + param(7) * (1 / (sqrt(2 * pi) * param(8))) * exp(-(LF - param(9)).^2 / (2 * param(8)^2));
    fcf = fcf / sum(fcf); % Compute the curve
    
    cf = sum((prob - fcf).^2); % Compute the error of mean square
    %cf = sum((histo - fcf).^2);
    
    subplot(3,2,4), plot(LF, prob, 'b', LF, fcf, 'r'); % Plot the real-time progress
    pause(0.0001);
    title('Work in Progress');
end