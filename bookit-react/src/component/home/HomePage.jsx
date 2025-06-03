import React, { useState } from "react";

const HomePage = () => {
  return (
    <div className="home">
      {/* HEADER / BANNER ROOM SECTION */}
      <section>
        <header className="header-banner">
          <img src="/assets/images/hotel.webp" alt="Phegon Hotel" className="header-image" />
          <div className="overlay"></div>
          <div className="animated-texts overlay-content">
            <h1>
              Welcome to <span className="phegon-color">Phegon Hotel</span>
            </h1><br />
            <h3>Step into a haven of comfort and care</h3>
          </div>
        </header>
      </section>

      {/* SEARCH/FIND AVAILABLE ROOM SECTION */}
      <h4><a className="view-rooms-home" href="/rooms">All Rooms</a></h4>
      <h2 className="home-services">Services at <span className="phegon-color">Phegon Hotel</span></h2>

      {/* SERVICES SECTION */}
      <section className="service-section">
        <div className="service-card">
          <img src="./assets/images/ac.png" alt="Air Conditioning" />
          <div className="service-details">
            <h3 className="service-title">Air Conditioning</h3>
            <p className="service-description">
              Stay cool and comfortable throughout your stay with our individual room AC.
            </p>
          </div>
        </div>

        <div className="service-card">
          <img src="./assets/images/mini-bar.png" alt="Mini Bar" />
          <div className="service-details">
            <h3 className="service-title">Mini Bar</h3>
            <p className="service-description">
              Enjoy a convenient selection of beverages and snacks stocked in your room.
            </p>
          </div>
        </div>
      </section>
    </div>
  );
};

export default HomePage;

